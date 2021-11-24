package com.example.springbootredis1;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class BloomDemo {
    public static void main(String[] args) {
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 10000000, 0.03);
        bloomFilter.put("chen");
        System.out.println(bloomFilter.mightContain("ccc"));
    }
}
