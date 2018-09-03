package com.dev.bamboo.bookingcrawler.controllers;

import com.dev.bamboo.bookingcrawler.domain.CrawledPriceInfo;
import com.dev.bamboo.bookingcrawler.models.Hotel;
import com.dev.bamboo.bookingcrawler.models.RoomDayPrice;
import com.dev.bamboo.bookingcrawler.repositories.HotelRepository;
import com.dev.bamboo.bookingcrawler.repositories.RoomDayPriceRepository;
import com.dev.bamboo.bookingcrawler.services.BookingCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TestController {

    @Autowired
    BookingCrawler bookingCrawler;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    RoomDayPriceRepository roomDayPriceRepository;

    @GetMapping("/crawl")
    public void testCrawler(@RequestParam("startdt") String startdt,@RequestParam("enddt") String enddt){

        //Optional<Hotel> h = hotelRepository.findById(1L);//findByHotelId("1351655");
        List<Hotel> lhotels = hotelRepository.findAll();

        for (Hotel hotel :lhotels){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = null;
            Date endDate = null;

            try {
                endDate = formatter.parse(enddt);
                startDate = formatter.parse(startdt);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {

                String url = hotel.getBaseUrl()+"?checkin="+date+";checkout="+date.plusDays(1);
                System.out.println(url);

                List<CrawledPriceInfo> lst = bookingCrawler.CrawlPageURL(url);

                for (CrawledPriceInfo cpi :lst){

                    RoomDayPrice roomDayPrice = new RoomDayPrice();

                    roomDayPrice.setCheckin(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    roomDayPrice.setCheckout(Date.from(date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));

                    roomDayPrice.setAvailable(cpi.getAvailable());
                    roomDayPrice.setPriceStr(cpi.getPrice());

                    if(cpi.getPrice() != null){
                        String s = cpi.getPrice().substring(2, cpi.getPrice().length()).replace(",",".");
                        Float d = Float.valueOf(s);
                        roomDayPrice.setPrice(d);
                    }

                    roomDayPrice.setRoomId(cpi.getRoomId());
                    roomDayPrice.setRoomName(cpi.getRoomName());
                    roomDayPrice.setOccupancy(cpi.getOccupancy());
                    roomDayPrice.setCrawlTime(Instant.now().getEpochSecond());

                    roomDayPrice.setHotel(hotel);

                    roomDayPriceRepository.saveAndFlush(roomDayPrice);

                }

            }
        }

    }

    @GetMapping("/crawlhotel")
    public void crawlHotel(@RequestParam("startdt") String startdt,@RequestParam("enddt") String enddt,@RequestParam("hotel_id")Long hotel_id){

        //Optional<Hotel> h = hotelRepository.findById(1L);//findByHotelId("1351655");
        Hotel hotel = hotelRepository.findById(hotel_id).get();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = null;
            Date endDate = null;

            try {
                endDate = formatter.parse(enddt);
                startDate = formatter.parse(startdt);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {

                String url = hotel.getBaseUrl()+"?checkin="+date+";checkout="+date.plusDays(1);
                System.out.println(url);

                List<CrawledPriceInfo> lst = bookingCrawler.CrawlPageURL(url);

                for (CrawledPriceInfo cpi :lst){

                    RoomDayPrice roomDayPrice = new RoomDayPrice();

                    roomDayPrice.setCheckin(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    roomDayPrice.setCheckout(Date.from(date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));

                    roomDayPrice.setAvailable(cpi.getAvailable());
                    roomDayPrice.setPriceStr(cpi.getPrice());

                    if(cpi.getPrice() != null){
                        String s = cpi.getPrice().substring(2, cpi.getPrice().length()).replace(",",".");
                        Float d = Float.valueOf(s);
                        roomDayPrice.setPrice(d);
                    }

                    roomDayPrice.setRoomId(cpi.getRoomId());
                    roomDayPrice.setRoomName(cpi.getRoomName());
                    roomDayPrice.setOccupancy(cpi.getOccupancy());
                    roomDayPrice.setCrawlTime(Instant.now().getEpochSecond());

                    roomDayPrice.setHotel(hotel);

                    roomDayPriceRepository.saveAndFlush(roomDayPrice);

                }

            }

    }



    @GetMapping("/createhotel")
    public void createHotel(){


/*
      Hotel h2 = new Hotel();
        h2.setHotelId("1965237");
        h2.setHotelName( "Dhome B&B");
        h2.setBaseUrl("https://www.booking.com/hotel/it/dhome-b-amp-b.it.html");

        hotelRepository.save(h2);




        Hotel h3 = new Hotel();
        h3.setHotelId("1239432  ");
        h3.setHotelName("Normarobby");
        h3.setBaseUrl("https://www.booking.com/hotel/it/normarobby-b-amp-b.it.html");

        hotelRepository.save(h3);


        Hotel h4 = new Hotel();
        h4.setHotelId("3701010");
        h4.setHotelName("Pamphili House Bed and Breakfast");
        h4.setBaseUrl("https://www.booking.com/hotel/it/chateau-al-gianicolo.it.html");

        hotelRepository.save(h4);


        Hotel h4 = new Hotel();
        h4.setHotelId("574737");
        h4.setHotelName("Miriam GuestHouse");
        h4.setBaseUrl("https://www.booking.com/hotel/it/miriam-guesthouse.it.html");

        hotelRepository.save(h4);

        */
        Hotel h4 = new Hotel();
        h4.setHotelId("349979");
        h4.setHotelName("Bamboo B&B Roma");
        h4.setBaseUrl("https://www.booking.com/hotel/it/bamboo-b.it.html");

        hotelRepository.save(h4);




    }



}
