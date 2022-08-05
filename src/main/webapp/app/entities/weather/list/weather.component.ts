import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWeather } from '../weather.model';
import { WeatherService } from '../service/weather.service';
import { WeatherDeleteDialogComponent } from '../delete/weather-delete-dialog.component';

@Component({
  selector: 'jhi-weather',
  templateUrl: './weather.component.html',
})
export class WeatherComponent implements OnInit {
  weathers?: IWeather[];
  isLoading = false;

  constructor(protected weatherService: WeatherService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.weatherService.query().subscribe({
      next: (res: HttpResponse<IWeather[]>) => {
        this.isLoading = false;
        this.weathers = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IWeather): number {
    return item.id!;
  }

  delete(weather: IWeather): void {
    const modalRef = this.modalService.open(WeatherDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.weather = weather;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
