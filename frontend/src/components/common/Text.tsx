import styles from "@/styles/Common.module.scss";

interface Text {
  text: string;
  fontWeight?: number;
  fontSize?: number;
  lineHeight?: number;
  color?: string;
  style?: any;
}

const getFontFamily = (weight: number) => {
  switch (weight) {
    case 400:
      return "Regular";
    case 500:
      return "Medium";
    case 600:
      return "SemiBold";
    case 700:
      return "Bold";
    default:
      return "Medium";
  }
};

export default function Text({
  text,
  fontWeight = 500,
  fontSize = 16,
  lineHeight = 22,
  color = "black",
  style,
}: Text) {
  const inlineStyle = {
    fontFamily: getFontFamily(fontWeight),
    fontSize: `${fontSize}px`,
    lineHeight: `${lineHeight}px`,
    color: color,
    ...style,
  };

  return (
    <span className={styles.text} style={inlineStyle}>
      {text}
    </span>
  );
}
