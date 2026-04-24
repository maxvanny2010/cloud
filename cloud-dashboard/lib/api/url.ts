export function gatewayUrl() {
    return process.env.GATEWAY_URL ?? "http://gateway:8072";
}