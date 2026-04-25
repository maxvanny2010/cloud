export default function CardSkeleton() {
    return (
        <div className="glass-card" style={{padding: 24}}>
            <div style={{display: "flex", justifyContent: "space-between", marginBottom: 18}}>
                <div style={{display: "flex", flexDirection: "column", gap: 8}}>
                    <div className="skeleton" style={{width: 36, height: 36, borderRadius: 8}}/>
                    <div className="skeleton" style={{width: 120, height: 14, borderRadius: 4}}/>
                    <div className="skeleton" style={{width: 80, height: 10, borderRadius: 4}}/>
                </div>
                <div className="skeleton" style={{width: 60, height: 22, borderRadius: 20}}/>
            </div>
            <div style={{display: "flex", flexDirection: "column", gap: 6}}>
                {[80, 60, 70].map((w, i) => (
                    <div key={i} style={{display: "flex", justifyContent: "space-between"}}>
                        <div className="skeleton" style={{width: `${w}px`, height: 10, borderRadius: 4}}/>
                        <div className="skeleton" style={{width: 30, height: 10, borderRadius: 4}}/>
                    </div>
                ))}
            </div>
        </div>
    );
}
