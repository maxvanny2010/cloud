"use server";
import type { License } from "../types";
import { ORG_IDS } from "../constants";

export async function getLicenses(): Promise<License[]> {
    const results = await Promise.allSettled(
        ORG_IDS.map((id) =>
            // LicenseController: @RequestMapping("v1/organization/{organizationId}/license")
            // getLicenses():     @GetMapping("/")
            // Gateway route:     /api/licenses/** → lb://LICENSE (strip /api/licenses)
            fetch(`${process.env.GATEWAY_URL}/api/licenses/v1/organization/${id}/license/`, { cache: "no-store" })
                .then((r) => {
                    if (!r.ok) throw new Error(`HTTP ${r.status}`);
                    return r.json() as Promise<License[]>;
                })
                .catch((e) => {
                    console.error(`[licenses] ERROR org=${id}:`, e.message);
                    throw e;
                })
        )
    );

    return results
        .filter((r): r is PromiseFulfilledResult<License[]> => r.status === "fulfilled")
        .flatMap((r) => r.value);
}