import RowSkeleton from "./RowSkeleton";

export default function ListSkeleton({ count = 3 }: { count?: number }) {
  return (
    <div style={{ display: "grid", gap: 14 }}>
      {Array.from({ length: count }, (_, i) => <RowSkeleton key={i} />)}
    </div>
  );
}
