"use server";
import type { Organization } from "../types";
import { ORG_IDS } from "@/app/constants";

export async function getOrganizations(): Promise<Organization[]> {
    const results = await Promise.allSettled(
        ORG_IDS.map((id) =>
            // OrganizationController: @RequestMapping("v1/organization")
            // getOrganization():      @GetMapping("/{organizationId}")
            // Gateway route:          /api/organizations/** → lb://ORGANIZATION (strip /api/organizations)
            fetch(`${process.env.GATEWAY_URL}/api/organizations/v1/organization/${id}`, { cache: "no-store" })
                .then((r) => {
                    console.log(`[organizations] ${id} → status ${r.status}`);
                    if (!r.ok) throw new Error(`HTTP ${r.status}`);
                    return r.json() as Promise<Organization>;
                })
                .catch((e) => {
                    console.error(`[organizations] ERROR org=${id}:`, e.message);
                    throw e;
                })
        )
    );

    const orgs = results
        .filter((r): r is PromiseFulfilledResult<Organization> => r.status === "fulfilled")
        .map((r) => r.value);

    console.log("[organizations] result count:", orgs.length);
    return orgs;
}