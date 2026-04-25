export default function RowSkeleton() {
    return (
        <div className="glass-card" style={{padding: "18px 24px"}}>
            <div style={{display: "flex", justifyContent: "space-between", alignItems: "center"}}>
                <div style={{display: "flex", alignItems: "center", gap: 14}}>
                    <div className="skeleton" style={{width: 44, height: 44, borderRadius: 12}}/>
                    <div style={{display: "flex", flexDirection: "column", gap: 6}}>
                        <div className="skeleton" style={{width: 140, height: 14, borderRadius: 4}}/>
                        <div className="skeleton" style={{width: 90, height: 10, borderRadius: 4}}/>
                    </div>
                </div>
                <div className="skeleton" style={{width: 60, height: 22, borderRadius: 20}}/>
            </div>
        </div>
    );
}
