package com.lzg.netty.protocoltcp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageProtocol {
    private int length;
    private byte[] content;
}
