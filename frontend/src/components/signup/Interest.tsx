import Text from "@/components/common/Text";
import styles from "@/styles/Entrance.module.scss";

const LIST_FIRST = [
  "경제/경영",
  "자기계발",
  "건강/의학",
  "예술/문학",
  "과학/기술",
  "소설",
  "취미/여행",
  "인문/사회",
];
const LIST_SECOND = [
  "IT/프로그램",
  "국어/외국어",
  "성공/처세",
  "가정생활",
  "주식/금융",
  "영업/판매",
  "인간관계",
];

interface Terms {
  onClickButton: () => void;
}

export default function Interest({ onClickButton }: Terms) {
  return (
    <div className={styles.interest}>
      <div className={styles.box}>
        {LIST_FIRST.map((v, i) => (
          <div key={i} className={styles.card}>
            <Text
              text={`#${v}`}
              fontSize={20}
              lineHeight={28}
              color="rgba(32, 32, 32, 0.2)"
            />
          </div>
        ))}
      </div>
      <div className={styles.box}>
        {LIST_SECOND.map((v, i) => (
          <div key={i} className={styles.card}>
            <Text
              text={`#${v}`}
              fontSize={20}
              lineHeight={28}
              color="rgba(32, 32, 32, 0.2)"
            />
          </div>
        ))}
      </div>

      <div className={`${styles.button}`} onClick={onClickButton}>
        <Text text="다음" fontSize={24} lineHeight={32} color="white" />
      </div>
    </div>
  );
}
