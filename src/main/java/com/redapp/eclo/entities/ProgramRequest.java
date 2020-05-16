package com.redapp.eclo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramRequest {
private Long month;
private Long year;
private String hijri;
private String status;

}
