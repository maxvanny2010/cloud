export default function Title() {
  return (
    <div>
      <p style={{
        fontSize: 10, letterSpacing: 4, color: "#44447a",
        fontFamily: "'Space Mono', monospace", marginBottom: 10,
        textTransform: "uppercase",
      }}>
        ◈ Microservices Platform
      </p>
      <h1 style={{
        fontSize: 44, fontWeight: 700, letterSpacing: -1.5,
        background: "linear-gradient(135deg, #ffffff 0%, #a0a0ff 45%, #5555cc 100%)",
        WebkitBackgroundClip: "text", WebkitTextFillColor: "transparent",
        lineHeight: 1.05, marginBottom: 10,
      }}>
        Control Tower
      </h1>
      <p style={{ fontSize: 13, color: "#44445a", fontFamily: "monospace" }}>
        Spring Boot 3.4 · Eureka · PostgreSQL · Docker
      </p>
    </div>
  );
}
