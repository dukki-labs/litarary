import { useState } from "react";
import Head from "next/head";
import styles from "@/styles/Entrance.module.scss";
import Title from "@/components/signup/Title";
import Terms from "@/components/signup/Terms";
import Interest from "@/components/signup/Interest";

export default function SignUp() {
  const [step, setStep] = useState<number>(1);
  const [terms, setTerms] = useState<boolean[]>([false, false, false]);

  const onClickTerm = (idx: number) => {
    if (idx === 3) {
      const agreeCnt = terms.filter((v) => v).length;
      if (agreeCnt === 3) {
        setTerms([false, false, false]);
        return;
      } else {
        setTerms([true, true, true]);
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

  const onClickButton = () => {
    if (step === 1 && terms[0] && terms[1]) {
      setStep(3);
      return;
    }
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
            {step === 1 && <div className={styles.step} />}
            {step === 2 && <div className={`${styles.step} ${styles.two}`} />}
            {step === 3 && <div className={`${styles.step} ${styles.three}`} />}
          </div>
          <Title step={step} />
          {step === 1 && (
            <Terms
              terms={terms}
              onClickTerm={onClickTerm}
              onClickButton={onClickButton}
            />
          )}
        </section>
        {step === 3 && <Interest onClickButton={onClickButton} />}
      </main>
    </>
  );
}
