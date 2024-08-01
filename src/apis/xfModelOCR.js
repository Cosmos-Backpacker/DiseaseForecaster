import http from "@/utils/http.js";

export default function ocrAPI(file)
{
    return http.post('/api/xfModel/OCR',file)
}