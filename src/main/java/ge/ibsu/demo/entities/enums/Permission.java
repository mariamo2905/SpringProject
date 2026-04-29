package ge.ibsu.demo.entities.enums;

public enum Permission {
    EMPLOYEE_READ("employee:read"), EMPLOYEE_ADD("employee:add");

    private String keyword;

    Permission(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
