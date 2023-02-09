import Text from "@/components/common/Text";
import styles from "@/styles/Entrance.module.scss";

interface Title {
  step: number;
}

export default function Title({ step }: Title) {
  return (
    <div className={styles.title}>
      {step === 1 && (
        <>
          <span>원활한 이용을 위해서</span>
          <br />
          <span>
            <span className={styles.bold}>리터러리 약관</span>에 동의해주세요.
          </span>
        </>
      )}
      {step === 2 && (
        <span>
          <span className={styles.bold}>기본정보를</span>등록해주세요.
        </span>
      )}
      {step === 3 && (
        <>
          <span>
            <span className={styles.bold}>관심사</span>를 선택해주세요.
          </span>
          <br />
          <Text
            text="최대 6개까지 선택할 수 있어요."
            fontSize={20}
            lineHeight={28}
            color="#999999"
          />
        </>
      )}
    </div>
  );
}
