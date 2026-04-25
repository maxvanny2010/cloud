export default function EmptyStateUI({message}: { message: string }) {
    return (
        <div style={{textAlign: "center", padding: 60, color: "#444466", fontSize: 14}}>
            {message}
        </div>
    );
}
