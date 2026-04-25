import type {NextConfig} from "next";

const GW = process.env.GATEWAY_URL ?? "http://gateway:8072";

const nextConfig: NextConfig = {
    env: {
        GATEWAY_URL: GW,
    },
    async rewrites() {
        return [
            {
                source: "/api/:path*",
                destination: `${GW}/api/:path*`,
            },
        ];
    },
};

export default nextConfig;