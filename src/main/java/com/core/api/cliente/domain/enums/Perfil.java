package com.core.api.cliente.domain.enums;

public enum Perfil {
    ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");

    private int code;
    private String description;

    Perfil(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }


    public String getDescription() {
        return description;
    }

    public static Perfil toEnum(Integer code) {
        Perfil perfil = null;

        if (code != null) {
            for (Perfil role : Perfil.values()) {
                if (code.equals(role.getCode())) {
                    perfil = role;
                }
            }

            if (perfil.equals(null)) {
                throw new IllegalArgumentException("Id inválido: " + code);
            }
        }

        return perfil;
    }
}
