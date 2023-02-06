import { useState } from "react";
import Head from "next/head";
import Image from "next/image";
import styles from "@/styles/Entrance.module.scss";

export default function SignIn() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  return (
    <>
      <Head>
        <title>signin</title>
        <meta name="description" content="로그인" />
      </Head>
      <main className={styles.signup}>
        <div className={styles.title}>
          <span>원활한 이용을 위해서</span>
          <br />
          <span>
            <span className={styles.bold}>리터러리 약관</span>에 동의해주세요.
          </span>
        </div>
        <div className={styles.signupWrapper}>
          <span>전체 동의</span>
        </div>
      </main>
    </>
  );
}
