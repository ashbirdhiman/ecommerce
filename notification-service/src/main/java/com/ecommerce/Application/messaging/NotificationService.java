package com.ecommerce.Application.messaging;

import com.ecommerce.domain.model.Notification;

public interface NotificationService {

     void send(Notification notification);
}
