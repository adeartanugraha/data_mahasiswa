package com.dimata.demo.kuliah.enums;

import lombok.Getter;

public enum GenderMahasiswa {
    LAKI_LAKI(0),
    PEREMPUAN(1),
    UNDEFINED(-1);

    @Getter
    private final int code;

    public static GenderMahasiswa getJenisKelamin(Integer code){
        switch (code) {
            case 0:
                return LAKI_LAKI;
            case 1:
                return PEREMPUAN;
            default:
                return UNDEFINED;
        }
    }

    GenderMahasiswa(int code) {
        this.code = code;
    }

    public String parseGender(GenderMahasiswa gender){
        if (gender == GenderMahasiswa.LAKI_LAKI) {
            return "Laki-Laki";
        }
        return "Perempuan";
    }

    public String parseGender(int code) {
        if (getJenisKelamin(code) == GenderMahasiswa.LAKI_LAKI) {
            return "Laki-Laki";
        } else if (getJenisKelamin(code) == GenderMahasiswa.PEREMPUAN) {
            return "Perempuan";
        }
        return "Undefined";
    }
}
