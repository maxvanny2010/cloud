"use server";

export interface EurekaApps {
    [name: string]: number;
}

export async function getEurekaApps(): Promise<EurekaApps> {
    try {
        const res = await fetch(`${process.env.GATEWAY_URL}/api/health/license`, { cache: "no-store" });
        const data = await res.json();
        return (
            data?.components?.discoveryComposite?.components?.eureka?.details?.applications ?? {}
        );
    } catch {
        return {};
    }
}
