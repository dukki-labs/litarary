import { useState } from "react";
import { useRouter } from "next/router";
import Head from "next/head";
import Image from "next/image";
import Text from "@/components/common/Text";
import styles from "@/styles/Entrance.module.scss";
import icon_eye_on from "@/img/icon_eye_on.svg";
import icon_eye_off from "@/img/icon_eye_off.svg";

export default function SignIn() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [active, setActive] = useState(0);
  const [isEyeOn, setIsEyeOn] = useState(false);
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
            <div className={`${styles.inputBox} ${active === 1 && styles.on}`}>
              <input
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="이메일"
                onFocus={() => setActive(1)}
                onBlur={() => setActive(0)}
              />
            </div>
            <div className={`${styles.inputBox} ${active === 2 && styles.on}`}>
              <input
                value={password}
                type={isEyeOn ? "text" : "password"}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="패스워드"
                onFocus={() => setActive(2)}
                onBlur={() => setActive(0)}
              />
              <Image
                src={isEyeOn ? icon_eye_on : icon_eye_off}
                alt=""
                onClick={() => setIsEyeOn(!isEyeOn)}
              />
            </div>
            <div
              className={`${styles.button} ${
                email && password ? styles.on : ""
              }`}
            >
              <Text
                text="로그인하기"
                fontSize={24}
                lineHeight={32}
                color="white"
              />
            </div>
            <div className={styles.lost}>
              <Text
                text="이런, 계정을 분실했나요?"
                fontSize={16}
                lineHeight={24}
                color="#1A1A1A"
              />
            </div>
            <div
              className={styles.signupButton}
              onClick={() => router.push("/signup")}
            >
              <span>리터러리 가입하기</span>
            </div>
          </div>
        </div>
      </main>
    </>
  );
}
