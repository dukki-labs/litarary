package com.litarary.book.service.aladdin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.litarary.account.domain.BookCategory;
import com.litarary.book.domain.entity.Category;
import com.litarary.book.domain.entity.CategoryMap;
import com.litarary.book.repository.CategoryMapRepository;
import com.litarary.book.repository.CategoryRepository;
import com.litarary.book.service.BookContainerService;
import com.litarary.book.service.aladdin.dto.AladdinBookDto;
import com.litarary.book.service.aladdin.dto.AladdinBookRequest;
import com.litarary.book.service.aladdin.dto.AladdinBookResponse;
import com.litarary.book.service.dto.ContainerBook;
import com.litarary.book.service.dto.ContainerBookInfo;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.LitararyErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AladdinBookServiceImpl implements BookContainerService {

    @Value("${book.container.aladdin}")
    private String secretKey;
    private final ObjectMapper objectMapper;
    private final CategoryMapRepository categoryMapRepository;
    private final CategoryRepository categoryRepository;

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;

    private final String endPointUrl = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx";

    @PostConstruct
    public void init() {
        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Transactional(readOnly = true)
    @Override
    public ContainerBookInfo searchBookList(String searchKeyword, Pageable pageable) {
        AladdinBookRequest requestEntity = AladdinBookRequest.createRequest(secretKey, searchKeyword, pageable);
        AladdinBookResponse response = requestBookInfo(requestEntity);

        return convertAladdinBookToContainerBook(response);
    }

    private ContainerBookInfo convertAladdinBookToContainerBook(AladdinBookResponse response) {
        List<ContainerBook> containerBooks = response.getItem()
                .stream()
                .map(item -> getContainerBook(item))
                .toList();

        final int totalResult = response.getTotalResults();
        final int page = response.getStartIndex();
        final int size = response.getItemsPerPage();
        final int totalPage = (int) Math.ceil(totalResult / (double) size);
        final boolean last = isLastPage(page, totalPage);

        return ContainerBookInfo.builder()
                .page(page)
                .size(size)
                .totalPage(totalPage)
                .last(last)
                .totalCount(response.getTotalResults())
                .searchKeyword(response.getQuery())
                .bookList(containerBooks)
                .build();
    }

    private boolean isLastPage(int page, int totalPage) {
        return page >= totalPage;
    }

    private ContainerBook getContainerBook(AladdinBookDto item) {
        Category category = getCategory(item.getCategoryId());
        return ContainerBook.builder()
                .title(item.getTitle())
                .author(item.getAuthor())
                .pubDate(item.getPubDate())
                .description(item.getDescription())
                .isbn(item.getIsbn())
                .isbn13(item.getIsbn13())
                .imageUrl(item.getCover())
                .categoryMapId(category.getId())
                .bookCategory(category.getBookCategory())
                .publisher(item.getPublisher())
                .build();
    }

    private Category getCategory(long categoryId) {
        CategoryMap categoryMap = categoryMapRepository.findByOriginalId(categoryId);
        if (categoryMap == null) {
            return categoryRepository.findByBookCategory(BookCategory.OTHER);
        }
        return categoryMap.getCategory();
    }

    private AladdinBookResponse requestBookInfo(AladdinBookRequest requestEntity) {
        URI uriString = createRequestUrl(this.endPointUrl, requestEntity);
        HttpEntity<AladdinBookRequest> requestHttpEntity = new HttpEntity<>(requestEntity, this.httpHeaders);

        try {
            String body = restTemplate.exchange(uriString, HttpMethod.GET, requestHttpEntity, String.class).getBody();
            return objectMapper.readValue(body, AladdinBookResponse.class);
        } catch (JsonProcessingException ex) {
            throw new LitararyErrorException(ErrorCode.JSON_PARSING_ERROR, ex.getMessage());
        } catch (RestClientException ex) {
            throw new LitararyErrorException(ErrorCode.EXTERNAL_REQUEST_ERROR, ex.getMessage());
        }
    }

    private URI createRequestUrl(String endPointUrl, AladdinBookRequest requestEntity) {
        return UriComponentsBuilder.fromUriString(endPointUrl)
                .queryParam("TTBKey", requestEntity.getSecretKey())
                .queryParam("Query", requestEntity.getQuery())
                .queryParam("QueryType", requestEntity.getQueryType())
                .queryParam("SearchTarget", requestEntity.getSearchTarget())
                .queryParam("Start", requestEntity.getStart())
                .queryParam("MaxResults", requestEntity.getMaxResults())
                .queryParam("Sort", requestEntity.getSort())
                .queryParam("Output", requestEntity.getOutput())
                .queryParam("Cover", requestEntity.getCover())
                .build()
                .encode()
                .toUri();
    }
}
