import React, {Suspense} from "react";
import Background from "./components/layout/Background";
import DashboardHeader from "./components/layout/DashboardHeader";
import DashboardFooter from "./components/layout/DashboardFooter";
import ServiceCard from "./components/ServiceCard";
import OrganizationsSection from "./components/OrganizationsSection";
import LicensesSection from "./components/LicensesSection";
import EurekaSection from "./components/EurekaSection";
import ServicesSkeleton from "./components/skeletons/ServicesSkeleton";
import ListSkeleton from "./components/skeletons/ListSkeleton";
import AutoRefresh from "./components/AutoRefresh";
import {SERVICES} from "./constants";

export const dynamic = "force-dynamic";

export default function DashboardPage() {
    return (
        <>
            <Background/>
            <AutoRefresh interval={15_000}/>
            <main style={{
                maxWidth: 1100, margin: "0 auto",
                padding: "44px 24px", position: "relative", zIndex: 1,
            }}>
                <DashboardHeader/>

                <Section title="Services" subtitle="Live health status · updates every 8s">
                    <Suspense fallback={<ServicesSkeleton/>}>
                        <div style={{
                            display: "grid",
                            gridTemplateColumns: "repeat(4, 1fr)",
                            gap: 16,
                        }}>
                            {SERVICES.map((s) => (
                                <ServiceCard key={s.name} service={s}/>
                            ))}
                        </div>
                    </Suspense>
                </Section>

                <Section title="Organizations" subtitle="Registered organizations from database · updates every 15s">
                    <Suspense fallback={<ListSkeleton count={3}/>}>
                        <OrganizationsSection/>
                    </Suspense>
                </Section>

                <Section title="Licenses" subtitle="All licenses across organizations · updates every 15s">
                    <Suspense fallback={<ListSkeleton count={4}/>}>
                        <LicensesSection/>
                    </Suspense>
                </Section>

                <Section title="Eureka Registry" subtitle="Registered microservices · updates every 15s">
                    <Suspense fallback={<ListSkeleton count={2}/>}>
                        <EurekaSection/>
                    </Suspense>
                </Section>

                <DashboardFooter/>
            </main>
        </>
    );
}

function Section({
                     title, subtitle, children,
                 }: {
    title: string;
    subtitle: string;
    children: React.ReactNode;
}) {
    return (
        <section style={{marginBottom: 52}}>
            <div style={{marginBottom: 20}}>
                <h2 style={{fontSize: 18, fontWeight: 600, color: "#e0e0f0", letterSpacing: -0.3}}>
                    {title}
                </h2>
                <p style={{fontSize: 11, color: "#44445a", fontFamily: "monospace", marginTop: 4}}>
                    {subtitle}
                </p>
            </div>
            {children}
        </section>
    );
}