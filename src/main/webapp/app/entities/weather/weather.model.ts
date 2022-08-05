import dayjs from 'dayjs/esm';
import { ICalendar } from 'app/entities/calendar/calendar.model';

export interface IWeather {
  id?: number;
  gUID?: string | null;
  cityID?: number | null;
  startTimestamp?: number | null;
  endTimestamp?: number | null;
  weatherStatusID?: number | null;
  temperature?: number | null;
  feelsLikeTemperature?: number | null;
  humidity?: number | null;
  windSpeed?: number | null;
  windDirection?: number | null;
  pressureinmmhg?: number | null;
  visibilityinmph?: number | null;
  cloudCover?: number | null;
  precipitation?: number | null;
  createdBy?: number | null;
  createdOn?: dayjs.Dayjs | null;
  updatedBy?: number | null;
  updatedOn?: dayjs.Dayjs | null;
  calendarID?: ICalendar | null;
}

export class Weather implements IWeather {
  constructor(
    public id?: number,
    public gUID?: string | null,
    public cityID?: number | null,
    public startTimestamp?: number | null,
    public endTimestamp?: number | null,
    public weatherStatusID?: number | null,
    public temperature?: number | null,
    public feelsLikeTemperature?: number | null,
    public humidity?: number | null,
    public windSpeed?: number | null,
    public windDirection?: number | null,
    public pressureinmmhg?: number | null,
    public visibilityinmph?: number | null,
    public cloudCover?: number | null,
    public precipitation?: number | null,
    public createdBy?: number | null,
    public createdOn?: dayjs.Dayjs | null,
    public updatedBy?: number | null,
    public updatedOn?: dayjs.Dayjs | null,
    public calendarID?: ICalendar | null
  ) {}
}

export function getWeatherIdentifier(weather: IWeather): number | undefined {
  return weather.id;
}
