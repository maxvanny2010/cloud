import { getOrganizations } from "../actions/organizations";
import { MESSAGES } from "../constants";
import OrgCardUI from "./ui/org/OrgCardUI";
import EmptyStateUI from "./ui/EmptyStateUI";

export default async function OrganizationsSection() {
  const orgs = await getOrganizations();

  if (orgs.length === 0) {
    return <EmptyStateUI message={MESSAGES.NO_ORGANIZATIONS} />;
  }

  return (
    <div style={{
      display: "grid",
      gridTemplateColumns: "repeat(auto-fill, minmax(300px, 1fr))",
      gap: 18,
    }}>
      {orgs.map((org) => (
        <OrgCardUI key={org.id} org={org} />
      ))}
    </div>
  );
}
