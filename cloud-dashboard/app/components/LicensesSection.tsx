import {getLicenses} from "../actions/licenses";
import {MESSAGES} from "../constants";
import LicenseRowUI from "./ui/license/LicenseRowUI";
import EmptyStateUI from "./ui/EmptyStateUI";

export default async function LicensesSection() {
    const licenses = await getLicenses();

    if (licenses.length === 0) {
        return <EmptyStateUI message={MESSAGES.NO_LICENSES}/>;
    }

    return (
        <div style={{display: "grid", gap: 14}}>
            {licenses.map((lic) => (
                <LicenseRowUI key={lic.licenseId} license={lic}/>
            ))}
        </div>
    );
}
