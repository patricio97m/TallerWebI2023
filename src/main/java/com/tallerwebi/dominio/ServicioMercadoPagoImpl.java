package com.tallerwebi.dominio;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadopago.resources.Preference;
import com.mercadopago.resources.Preference.AutoReturn;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;

@Service("servicioMercadoPago")
@Transactional
public class ServicioMercadoPagoImpl implements ServicioMercadoPago{

    @Autowired
    public ServicioMercadoPagoImpl(){

    }

    @Override
    public Preference crearPreferencia() {
        Preference preference = new Preference();

        BackUrls backurls = new BackUrls()
        .setFailure("localhost:8080/spring/pagoFallido")
        .setSuccess("localhost:8080/spring/pagoAprobado");
        preference.setBackUrls(backurls);

        Item item = new Item()
        .setTitle("Potenciador de Prueba")
        .setQuantity(1)
        .setUnitPrice((float)100.00);

        preference.appendItem(item);
        preference.setAutoReturn(AutoReturn.approved);
        preference.setBinaryMode(true);

        return preference;
    }

    
}


// PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
//         .id("item-ID-1234")
//         .title("Meu produto")
//         .currencyId("BRL")
//         .pictureUrl("https://www.mercadopago.com/org-img/MP3/home/logomp3.gif")
//         .description("Descrição do Item")
//         .categoryId("art")
//         .quantity(1)
//         .unitPrice(new BigDecimal("75.76"))
//         .build();

//         ArrayList<PreferenceItemRequest> items = new ArrayList<>();
//         items.add(itemRequest);

//         IdentificationRequest identification = IdentificationRequest.builder()
//         .type("DNI")
//         .number("19119119100")
//         .build();

//         AddressRequest address = AddressRequest.builder()
//         .streetName("Street")
//         .streetNumber("123")
//         .zipCode("06233200")
//         .build();

//         PhoneRequest phone = PhoneRequest.builder()
//         .areaCode("11")
//         .number("4444-4444")
//         .build();

//         PreferencePayerRequest payer = PreferencePayerRequest.builder()
//         .name("João")
//         .surname("Silva")
//         .email("user@email.com")
//         .phone(phone)
//         .address(address)
//         .identification(identification)
//         .build();

//         PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
//         .success("https://www.success.com")
//         .failure("http://www.failure.com")
//         .build();

//         PreferencePaymentMethodsRequest paymentMethods = PreferencePaymentMethodsRequest.builder()
//         .installments(1)
//         .build();

//         DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
//         OffsetDateTime expirationDateFrom = OffsetDateTime.parse("2016-02-01T12:00:00.000-04:00", formatter);
//         OffsetDateTime expirationDateTo = OffsetDateTime.parse("2016-02-28T12:00:00.000-04:00", formatter);


//         //CREACION DE LA PREFERENCIA
//         PreferenceRequest preferenceRequest = PreferenceRequest.builder()
//                 .items(items)
//                 .payer(payer)
//                 .backUrls(backUrls)
//                 .autoReturn("approved")
//                 .binaryMode(true)
//                 .paymentMethods(paymentMethods)
//                 .notificationUrl("https://www.your-site.com/ipn")
//                 .statementDescriptor("MEUNEGOCIO")
//                 .externalReference("Reference_1234")
//                 .expires(true)
//                 .expirationDateFrom(expirationDateFrom)
//                 .expirationDateTo(expirationDateTo)
//                 .build();

//         return preferenceRequest;
