import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { WeatherService } from '../service/weather.service';
import { IWeather, Weather } from '../weather.model';
import { ICalendar } from 'app/entities/calendar/calendar.model';
import { CalendarService } from 'app/entities/calendar/service/calendar.service';

import { WeatherUpdateComponent } from './weather-update.component';

describe('Weather Management Update Component', () => {
  let comp: WeatherUpdateComponent;
  let fixture: ComponentFixture<WeatherUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let weatherService: WeatherService;
  let calendarService: CalendarService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [WeatherUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(WeatherUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WeatherUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    weatherService = TestBed.inject(WeatherService);
    calendarService = TestBed.inject(CalendarService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Calendar query and add missing value', () => {
      const weather: IWeather = { id: 456 };
      const calendarID: ICalendar = { id: 50325 };
      weather.calendarID = calendarID;

      const calendarCollection: ICalendar[] = [{ id: 13965 }];
      jest.spyOn(calendarService, 'query').mockReturnValue(of(new HttpResponse({ body: calendarCollection })));
      const additionalCalendars = [calendarID];
      const expectedCollection: ICalendar[] = [...additionalCalendars, ...calendarCollection];
      jest.spyOn(calendarService, 'addCalendarToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ weather });
      comp.ngOnInit();

      expect(calendarService.query).toHaveBeenCalled();
      expect(calendarService.addCalendarToCollectionIfMissing).toHaveBeenCalledWith(calendarCollection, ...additionalCalendars);
      expect(comp.calendarsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const weather: IWeather = { id: 456 };
      const calendarID: ICalendar = { id: 50580 };
      weather.calendarID = calendarID;

      activatedRoute.data = of({ weather });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(weather));
      expect(comp.calendarsSharedCollection).toContain(calendarID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Weather>>();
      const weather = { id: 123 };
      jest.spyOn(weatherService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ weather });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: weather }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(weatherService.update).toHaveBeenCalledWith(weather);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Weather>>();
      const weather = new Weather();
      jest.spyOn(weatherService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ weather });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: weather }));
      saveSubject.complete();

      // THEN
      expect(weatherService.create).toHaveBeenCalledWith(weather);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Weather>>();
      const weather = { id: 123 };
      jest.spyOn(weatherService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ weather });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(weatherService.update).toHaveBeenCalledWith(weather);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCalendarById', () => {
      it('Should return tracked Calendar primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCalendarById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
