import Image from "next/image";
import Text from "@/components/common/Text";
import styles from "@/styles/Entrance.module.scss";
import icon_check_on from "@/img/icon_check_on.svg";
import icon_check_off from "@/img/icon_check_off.svg";

interface Terms {
  terms: boolean[];
  onClickTerm: (i: number) => void;
  onClickButton: () => void;
}

export default function Terms({ terms, onClickTerm, onClickButton }: Terms) {
  return (
    <div className={styles.signupWrapper}>
      <div className={styles.allAgree}>
        <Image
          src={
            terms[0] && terms[1] && terms[2] ? icon_check_on : icon_check_off
          }
          alt=""
          onClick={() => onClickTerm(3)}
        />
        <Text text="전체 동의 (선택 포함)" fontSize={20} lineHeight={28} />
      </div>
      <div className={styles.termsBox}>
        <div className={styles.terms}>
          <Image
            src={terms[0] ? icon_check_on : icon_check_off}
            alt=""
            onClick={() => onClickTerm(0)}
          />
          <Text
            text="[필수] 리터러리 이용 약관에 동의합니다."
            fontSize={20}
            lineHeight={28}
          />
        </div>
        <div className={styles.termsDesc}>
          <Text
            text={`계정 약관 동의 텍스트\n계정 약관 동의 텍스트\n계정 약관 동의 텍스트\n계정 약관 동의 텍스트\n계정 약관 동의 텍스트\n계정 약관 동의 텍스트\n계정 약관 동의 텍스트`}
            fontWeight={400}
            fontSize={16}
            lineHeight={24}
            color="#999999"
          />
        </div>
      </div>
      <div className={styles.termsBox}>
        <div className={styles.terms}>
          <Image
            src={terms[1] ? icon_check_on : icon_check_off}
            alt=""
            onClick={() => onClickTerm(1)}
          />
          <Text
            text="[필수] 개인정보 수집 및 이용에 동의합니다."
            fontSize={20}
            lineHeight={28}
          />
        </div>
        <div className={styles.termsDesc}>
          <Text
            text="서비스 약관 동의 텍스트"
            fontWeight={400}
            fontSize={16}
            lineHeight={24}
            color="#999999"
          />
        </div>
      </div>
      <div className={styles.termsBox}>
        <div className={styles.terms}>
          <Image
            src={terms[2] ? icon_check_on : icon_check_off}
            alt=""
            onClick={() => onClickTerm(2)}
          />
          <Text
            text="[선택] 이메일 수신 여부에 동의합니다."
            fontSize={20}
            lineHeight={28}
          />
        </div>
        <div className={styles.termsDesc}>
          <Text
            text="개인정보 수집 및 이용 안내 텍스트"
            fontWeight={400}
            fontSize={16}
            lineHeight={24}
            color="#999999"
          />
        </div>
      </div>
      <div
        className={`${styles.button} ${terms[0] && terms[1] ? styles.on : ""}`}
        onClick={onClickButton}
      >
        <Text text="다음" fontSize={24} lineHeight={32} color="white" />
      </div>
    </div>
  );
}
