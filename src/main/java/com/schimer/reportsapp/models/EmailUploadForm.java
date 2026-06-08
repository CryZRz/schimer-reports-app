package com.schimer.reportsapp.models;

import javafx.beans.property.StringProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailUploadForm {
    private StringProperty affair;
    private StringProperty email;
    private StringProperty body;
}
