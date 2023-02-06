import { useState } from "react";
import { useRouter } from "next/router";
import Head from "next/head";
import Text from "@/components/common/Text";
import styles from "@/styles/Entrance.module.scss";

export default function SignIn() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const router = useRouter();

  return (
    <>
      <Head>
        <title>signin</title>
        <meta name="description" content="로그인" />
      </Head>
      <main className={styles.signin}>
        <div className={styles.signinWrapper}>
          <div className={styles.title}>
            <span>우리만의 작은 도서관</span>
            <br />
            <span>
              <span className={styles.bold}>리터러리</span>를 만나보세요!
            </span>
          </div>
          <div className={styles.singinBox}>
            <div className={styles.inputBox}>
              <input
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="이메일"
              />
            </div>
            <div className={styles.inputBox}>
              <input
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="패스워드"
              />
            </div>
            <div
              className={`${styles.button} ${
                email && password ? styles.on : ""
              }`}
            >
              <Text text="로그인" fontSize={24} lineHeight={32} color="white" />
            </div>
            <div className={styles.etc}>
              <div onClick={() => router.push("/signup")}>
                <Text text="회원가입" fontSize={20} lineHeight={28} />
              </div>
              <Text text="패스워드 찾기" fontSize={20} lineHeight={28} />
            </div>
          </div>
        </div>
      </main>
    </>
  );
}
