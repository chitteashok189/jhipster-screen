import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IWeather, Weather } from '../weather.model';

import { WeatherService } from './weather.service';

describe('Weather Service', () => {
  let service: WeatherService;
  let httpMock: HttpTestingController;
  let elemDefault: IWeather;
  let expectedResult: IWeather | IWeather[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(WeatherService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      gUID: 'AAAAAAA',
      cityID: 0,
      startTimestamp: 0,
      endTimestamp: 0,
      weatherStatusID: 0,
      temperature: 0,
      feelsLikeTemperature: 0,
      humidity: 0,
      windSpeed: 0,
      windDirection: 0,
      pressureinmmhg: 0,
      visibilityinmph: 0,
      cloudCover: 0,
      precipitation: 0,
      createdBy: 0,
      createdOn: currentDate,
      updatedBy: 0,
      updatedOn: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Weather', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.create(new Weather()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Weather', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          cityID: 1,
          startTimestamp: 1,
          endTimestamp: 1,
          weatherStatusID: 1,
          temperature: 1,
          feelsLikeTemperature: 1,
          humidity: 1,
          windSpeed: 1,
          windDirection: 1,
          pressureinmmhg: 1,
          visibilityinmph: 1,
          cloudCover: 1,
          precipitation: 1,
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Weather', () => {
      const patchObject = Object.assign(
        {
          gUID: 'BBBBBB',
          cityID: 1,
          startTimestamp: 1,
          endTimestamp: 1,
          feelsLikeTemperature: 1,
          humidity: 1,
          windSpeed: 1,
          windDirection: 1,
          visibilityinmph: 1,
          precipitation: 1,
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        new Weather()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Weather', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          gUID: 'BBBBBB',
          cityID: 1,
          startTimestamp: 1,
          endTimestamp: 1,
          weatherStatusID: 1,
          temperature: 1,
          feelsLikeTemperature: 1,
          humidity: 1,
          windSpeed: 1,
          windDirection: 1,
          pressureinmmhg: 1,
          visibilityinmph: 1,
          cloudCover: 1,
          precipitation: 1,
          createdBy: 1,
          createdOn: currentDate.format(DATE_TIME_FORMAT),
          updatedBy: 1,
          updatedOn: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          createdOn: currentDate,
          updatedOn: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Weather', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addWeatherToCollectionIfMissing', () => {
      it('should add a Weather to an empty array', () => {
        const weather: IWeather = { id: 123 };
        expectedResult = service.addWeatherToCollectionIfMissing([], weather);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(weather);
      });

      it('should not add a Weather to an array that contains it', () => {
        const weather: IWeather = { id: 123 };
        const weatherCollection: IWeather[] = [
          {
            ...weather,
          },
          { id: 456 },
        ];
        expectedResult = service.addWeatherToCollectionIfMissing(weatherCollection, weather);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Weather to an array that doesn't contain it", () => {
        const weather: IWeather = { id: 123 };
        const weatherCollection: IWeather[] = [{ id: 456 }];
        expectedResult = service.addWeatherToCollectionIfMissing(weatherCollection, weather);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(weather);
      });

      it('should add only unique Weather to an array', () => {
        const weatherArray: IWeather[] = [{ id: 123 }, { id: 456 }, { id: 78956 }];
        const weatherCollection: IWeather[] = [{ id: 123 }];
        expectedResult = service.addWeatherToCollectionIfMissing(weatherCollection, ...weatherArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const weather: IWeather = { id: 123 };
        const weather2: IWeather = { id: 456 };
        expectedResult = service.addWeatherToCollectionIfMissing([], weather, weather2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(weather);
        expect(expectedResult).toContain(weather2);
      });

      it('should accept null and undefined values', () => {
        const weather: IWeather = { id: 123 };
        expectedResult = service.addWeatherToCollectionIfMissing([], null, weather, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(weather);
      });

      it('should return initial array if no Weather is added', () => {
        const weatherCollection: IWeather[] = [{ id: 123 }];
        expectedResult = service.addWeatherToCollectionIfMissing(weatherCollection, undefined, null);
        expect(expectedResult).toEqual(weatherCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
