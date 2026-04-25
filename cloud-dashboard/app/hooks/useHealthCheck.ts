"use client";
import {useEffect, useReducer} from "react";
import {HEALTH_ACTION, POLL_INTERVAL, SERVICE_STATUS} from "../constants";
import type {HealthAction, HealthStatus, ServiceStatus} from "../types";

interface State {
    data: HealthStatus | null;
    status: ServiceStatus;
}

const INITIAL_STATE: State = {
    data: null,
    status: SERVICE_STATUS.LOADING,
};

function reducer(state: State, action: HealthAction): State {
    switch (action.type) {
        case HEALTH_ACTION.SUCCESS:
            return {data: action.payload, status: SERVICE_STATUS.UP};
        case HEALTH_ACTION.ERROR:
            return {data: null, status: SERVICE_STATUS.DOWN};
        default:
            return state;
    }
}

export function useHealthCheck(
    url: string,
    interval: number = POLL_INTERVAL.DEFAULT
): State {
    const [state, dispatch] = useReducer(reducer, INITIAL_STATE);

    useEffect(() => {
        const controller = new AbortController();

        const check = () =>
            fetch(url, {signal: controller.signal})
                .then((r) => r.json())
                .then((data: HealthStatus) =>
                    dispatch({type: HEALTH_ACTION.SUCCESS, payload: data})
                )
                .catch((err) => {
                    if (err.name !== "AbortError") {
                        dispatch({type: HEALTH_ACTION.ERROR});
                    }
                });

        check();
        const id = setInterval(check, interval);
        return () => {
            clearInterval(id);
            controller.abort();
        };
    }, [url, interval]);

    return state;
}
