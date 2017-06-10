package model;

import java.util.List;

import lombok.Data;

@Data
public class Email {

        private List<String> toEmail;
        private String fromEmail;
        private String subject;
        private String body;

        public boolean isValid() {
            return !toEmail.isEmpty() && !(fromEmail == null) && !(subject == null);
        }
    }

