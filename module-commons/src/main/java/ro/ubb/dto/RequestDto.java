package ro.ubb.dto;

import java.io.Serializable;

public class RequestDto implements Serializable {

    private final String requestType;
    private final CarDto carDto;

    public RequestDto(String requestType, CarDto carDto) {
        this.requestType = requestType;
        this.carDto = carDto;
    }

    public String getRequestType() {
        return requestType;
    }

    public CarDto getCarDto() {
        return carDto;
    }

    @Override
    public String toString() {
        return "RequestDto{" +
                "requestType='" + requestType + '\'' +
                ", carDto=" + carDto +
                '}';
    }
}
