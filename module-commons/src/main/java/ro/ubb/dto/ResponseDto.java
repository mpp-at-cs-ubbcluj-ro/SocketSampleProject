package ro.ubb.dto;

import java.io.Serializable;
import java.util.List;

public class ResponseDto implements Serializable {

    private final String responseType;
    private final String message;
    private final List<CarDto> carDtoList;

    public ResponseDto(String responseType, String message, List<CarDto> carDtoList) {
        this.responseType = responseType;
        this.message = message;
        this.carDtoList = carDtoList;
    }

    public String getResponseType() {
        return responseType;
    }

    public String getMessage() {
        return message;
    }

    public List<CarDto> getCarDtoList() {
        return carDtoList;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "message='" + message + '\'' +
                ", carDtoList=" + carDtoList +
                '}';
    }
}
