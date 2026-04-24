import type { Organization } from "../../../types";
import OrgHeader from "./OrgHeader";
import OrgContacts from "./OrgContacts";

export default function OrgCardUI({ org }: { org: Organization }) {
  return (
    <div className="glass-card" style={{ padding: "20px 24px" }}>
      <OrgHeader org={org} />
      <OrgContacts org={org} />
    </div>
  );
}
