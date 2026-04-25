const ORBS = [
    {top: "8%", left: "10%", size: 500, color: "rgba(80,60,220,0.07)", dur: 10},
    {top: "55%", right: "8%", size: 450, color: "rgba(0,180,255,0.06)", dur: 13},
    {top: "35%", left: "45%", size: 350, color: "rgba(200,80,255,0.05)", dur: 9},
] as const;

export default function Background() {
    return (
        <div style={{position: "fixed", inset: 0, pointerEvents: "none", overflow: "hidden", zIndex: 0}}>
            {ORBS.map((orb, i) => (
                <div key={i} style={{
                    position: "absolute",
                    top: orb.top,
                    left: (orb as unknown as Record<string, string>).left,
                    right: (orb as unknown as Record<string, string>).right,
                    width: orb.size, height: orb.size,
                    borderRadius: "50%", background: orb.color,
                    filter: "blur(90px)",
                    animation: `float-orb ${orb.dur}s ease-in-out infinite`,
                    animationDelay: `${i * 2.5}s`,
                }}/>
            ))}
            <div style={{
                position: "absolute", inset: 0,
                backgroundImage: `
          linear-gradient(rgba(255,255,255,0.018) 1px, transparent 1px),
          linear-gradient(90deg, rgba(255,255,255,0.018) 1px, transparent 1px)
        `,
                backgroundSize: "64px 64px",
            }}/>
        </div>
    );
}
