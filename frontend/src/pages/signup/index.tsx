import { useState } from "react";
import Head from "next/head";
import Text from "@/components/common/Text";
import styles from "@/styles/Entrance.module.scss";

export default function SignIn() {
  const [terms, setTerms] = useState<boolean[]>([false, false, false, false]);

  const onClickTerm = (idx: number) => {
    if (idx === 4) {
      const agreeCnt = terms.filter((v) => v).length;
      if (agreeCnt === 4) {
        setTerms([false, false, false, false]);
        return;
      } else {
        setTerms([true, true, true, true]);
        return;
      }
    }
    setTerms((prev) => {
      return prev.map((v, i) => {
        if (i === idx) {
          return !v;
        }
        return v;
      });
    });
  };

  return (
    <>
      <Head>
        <title>signup</title>
        <meta name="description" content="회원가입" />
      </Head>
      <main className={styles.signup}>
        <section>
          <div className={styles.bar}>
            <div className={styles.progress}></div>
            <div className={styles.step}></div>
          </div>
          <div className={styles.title}>
            <span>원활한 이용을 위해서</span>
            <br />
            <span>
              <span className={styles.bold}>리터러리 약관</span>에 동의해주세요.
            </span>
          </div>
          <div className={styles.signupWrapper}>
            <div className={styles.allAgree}>
              <div
                className={`${styles.check} ${
                  terms[0] && terms[1] && terms[2] && terms[3] ? styles.on : ""
                }`}
                onClick={() => onClickTerm(4)}
              />
              <Text
                text="전체 동의 (선택 포함)"
                fontSize={20}
                lineHeight={28}
              />
            </div>
            <div className={styles.termsBox}>
              <div className={styles.terms}>
                <div
                  className={`${styles.check} ${terms[0] ? styles.on : ""}`}
                  onClick={() => onClickTerm(0)}
                />
                <Text
                  text="(필수) 리터러리 계정 약관 동의"
                  fontSize={20}
                  lineHeight={28}
                />
              </div>
              <div className={styles.termsDesc}>
                <Text
                  text="계정 약관 동의 텍스트"
                  fontWeight={400}
                  fontSize={12}
                  lineHeight={16}
                />
              </div>
            </div>
            <div className={styles.termsBox}>
              <div className={styles.terms}>
                <div
                  className={`${styles.check} ${terms[1] ? styles.on : ""}`}
                  onClick={() => onClickTerm(1)}
                />
                <Text
                  text="(필수) 리터러리 서비스 약관 동의"
                  fontSize={20}
                  lineHeight={28}
                />
              </div>
              <div className={styles.termsDesc}>
                <Text
                  text="서비스 약관 동의 텍스트"
                  fontWeight={400}
                  fontSize={12}
                  lineHeight={16}
                />
              </div>
            </div>
            <div className={styles.termsBox}>
              <div className={styles.terms}>
                <div
                  className={`${styles.check} ${terms[2] ? styles.on : ""}`}
                  onClick={() => onClickTerm(2)}
                />
                <Text
                  text="(필수) 개인정보 수집 및 이용 안내"
                  fontSize={20}
                  lineHeight={28}
                />
              </div>
              <div className={styles.termsDesc}>
                <Text
                  text="개인정보 수집 및 이용 안내 텍스트"
                  fontWeight={400}
                  fontSize={12}
                  lineHeight={16}
                />
              </div>
            </div>
            <div className={styles.termsBox}>
              <div className={styles.terms}>
                <div
                  className={`${styles.check} ${terms[3] ? styles.on : ""}`}
                  onClick={() => onClickTerm(3)}
                />
                <Text
                  text="(선택) 서비스 안내 수신 동의"
                  fontSize={20}
                  lineHeight={28}
                />
              </div>
            </div>
            <div
              className={`${styles.button} ${
                terms[0] && terms[1] && terms[2] ? styles.on : ""
              }`}
            >
              <Text text="다음" fontSize={24} lineHeight={32} color="white" />
            </div>
          </div>
        </section>
      </main>
    </>
  );
}
