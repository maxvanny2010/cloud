import CardSkeleton from "./CardSkeleton";

export default function ServicesSkeleton() {
  return (
    <div style={{
      display: "grid",
      gridTemplateColumns: "repeat(auto-fill, minmax(240px, 1fr))",
      gap: 20,
    }}>
      {[1, 2, 3, 4].map((i) => <CardSkeleton key={i} />)}
    </div>
  );
}
