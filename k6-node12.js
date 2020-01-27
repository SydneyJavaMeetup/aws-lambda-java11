import http from "k6/http";
import { check, sleep } from "k6";

export let options = {
    stages: [
        { duration: "30s", target: 20 },
        { duration: "1m30s" },
        { duration: "30s", target: 0 },
    ]
};

export default function() {
    let res = http.get("https://api.mycodefu.com/language-test/node12");
    check(res, {
        "status was 200": (r) => r.status == 200
    });
    // sleep(.1);
};