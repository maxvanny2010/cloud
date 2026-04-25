"use client";
import {useHealthCheck} from "../hooks/useHealthCheck";
import {POLL_INTERVAL} from "../constants";
import type {ServiceConfig} from "../types";
import ServiceCardUI from "./ui/service/ServiceCardUI";

export default function ServiceCard({service}: { service: ServiceConfig }) {
    const {data, status} = useHealthCheck(service.url, POLL_INTERVAL.SERVICES);
    return <ServiceCardUI service={service} status={status} data={data}/>;
}
