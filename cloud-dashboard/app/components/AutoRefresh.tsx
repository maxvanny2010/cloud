"use client";
import {useEffect} from "react";
import {useRouter} from "next/navigation";

interface Props {
    interval?: number; // ms, default 15s
}

export default function AutoRefresh({interval = 15_000}: Props) {
    const router = useRouter();

    useEffect(() => {
        const id = setInterval(() => router.refresh(), interval);
        return () => clearInterval(id);
    }, [router, interval]);

    return null;
}