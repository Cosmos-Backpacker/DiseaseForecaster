package com.forecaster.bean.Ocr;

import com.forecaster.bean.Ocr.Header;
import com.forecaster.bean.Ocr.Payload;
import lombok.Data;


@Data
public class JsonParseOcr {
    private Header header;
    private Payload payload;
}
