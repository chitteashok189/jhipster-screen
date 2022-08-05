import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IWeather, Weather } from '../weather.model';
import { WeatherService } from '../service/weather.service';
import { ICalendar } from 'app/entities/calendar/calendar.model';
import { CalendarService } from 'app/entities/calendar/service/calendar.service';

@Component({
  selector: 'jhi-weather-update',
  templateUrl: './weather-update.component.html',
})
export class WeatherUpdateComponent implements OnInit {
  isSaving = false;

  calendarsSharedCollection: ICalendar[] = [];

  editForm = this.fb.group({
    id: [],
    gUID: [],
    cityID: [],
    startTimestamp: [],
    endTimestamp: [],
    weatherStatusID: [],
    temperature: [],
    feelsLikeTemperature: [],
    humidity: [],
    windSpeed: [],
    windDirection: [],
    pressureinmmhg: [],
    visibilityinmph: [],
    cloudCover: [],
    precipitation: [],
    createdBy: [],
    createdOn: [],
    updatedBy: [],
    updatedOn: [],
    calendarID: [],
  });

  constructor(
    protected weatherService: WeatherService,
    protected calendarService: CalendarService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ weather }) => {
      if (weather.id === undefined) {
        const today = dayjs().startOf('day');
        weather.createdOn = today;
        weather.updatedOn = today;
      }

      this.updateForm(weather);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const weather = this.createFromForm();
    if (weather.id !== undefined) {
      this.subscribeToSaveResponse(this.weatherService.update(weather));
    } else {
      this.subscribeToSaveResponse(this.weatherService.create(weather));
    }
  }

  trackCalendarById(_index: number, item: ICalendar): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWeather>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(weather: IWeather): void {
    this.editForm.patchValue({
      id: weather.id,
      gUID: weather.gUID,
      cityID: weather.cityID,
      startTimestamp: weather.startTimestamp,
      endTimestamp: weather.endTimestamp,
      weatherStatusID: weather.weatherStatusID,
      temperature: weather.temperature,
      feelsLikeTemperature: weather.feelsLikeTemperature,
      humidity: weather.humidity,
      windSpeed: weather.windSpeed,
      windDirection: weather.windDirection,
      pressureinmmhg: weather.pressureinmmhg,
      visibilityinmph: weather.visibilityinmph,
      cloudCover: weather.cloudCover,
      precipitation: weather.precipitation,
      createdBy: weather.createdBy,
      createdOn: weather.createdOn ? weather.createdOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: weather.updatedBy,
      updatedOn: weather.updatedOn ? weather.updatedOn.format(DATE_TIME_FORMAT) : null,
      calendarID: weather.calendarID,
    });

    this.calendarsSharedCollection = this.calendarService.addCalendarToCollectionIfMissing(
      this.calendarsSharedCollection,
      weather.calendarID
    );
  }

  protected loadRelationshipsOptions(): void {
    this.calendarService
      .query()
      .pipe(map((res: HttpResponse<ICalendar[]>) => res.body ?? []))
      .pipe(
        map((calendars: ICalendar[]) =>
          this.calendarService.addCalendarToCollectionIfMissing(calendars, this.editForm.get('calendarID')!.value)
        )
      )
      .subscribe((calendars: ICalendar[]) => (this.calendarsSharedCollection = calendars));
  }

  protected createFromForm(): IWeather {
    return {
      ...new Weather(),
      id: this.editForm.get(['id'])!.value,
      gUID: this.editForm.get(['gUID'])!.value,
      cityID: this.editForm.get(['cityID'])!.value,
      startTimestamp: this.editForm.get(['startTimestamp'])!.value,
      endTimestamp: this.editForm.get(['endTimestamp'])!.value,
      weatherStatusID: this.editForm.get(['weatherStatusID'])!.value,
      temperature: this.editForm.get(['temperature'])!.value,
      feelsLikeTemperature: this.editForm.get(['feelsLikeTemperature'])!.value,
      humidity: this.editForm.get(['humidity'])!.value,
      windSpeed: this.editForm.get(['windSpeed'])!.value,
      windDirection: this.editForm.get(['windDirection'])!.value,
      pressureinmmhg: this.editForm.get(['pressureinmmhg'])!.value,
      visibilityinmph: this.editForm.get(['visibilityinmph'])!.value,
      cloudCover: this.editForm.get(['cloudCover'])!.value,
      precipitation: this.editForm.get(['precipitation'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? dayjs(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? dayjs(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      calendarID: this.editForm.get(['calendarID'])!.value,
    };
  }
}
