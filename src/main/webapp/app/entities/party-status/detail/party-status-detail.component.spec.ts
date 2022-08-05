import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PartyStatusDetailComponent } from './party-status-detail.component';

describe('PartyStatus Management Detail Component', () => {
  let comp: PartyStatusDetailComponent;
  let fixture: ComponentFixture<PartyStatusDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PartyStatusDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ partyStatus: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PartyStatusDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PartyStatusDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load partyStatus on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.partyStatus).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
