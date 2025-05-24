package vn.com.pharmacity.base;

public interface BaseMapper<E extends BaseModel, D extends BaseDto> {
    D toDto(E entity);

    E toEntity(D dto);
}
