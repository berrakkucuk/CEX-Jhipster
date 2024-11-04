/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import SeatUpdate from './seat-update.vue';
import SeatService from './seat.service';
import AlertService from '@/shared/alert/alert.service';

import BookingService from '@/entities/booking/booking.service';
import ScreenService from '@/entities/screen/screen.service';

type SeatUpdateComponentType = InstanceType<typeof SeatUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const seatSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<SeatUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Seat Management Update Component', () => {
    let comp: SeatUpdateComponentType;
    let seatServiceStub: SinonStubbedInstance<SeatService>;

    beforeEach(() => {
      route = {};
      seatServiceStub = sinon.createStubInstance<SeatService>(SeatService);
      seatServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          seatService: () => seatServiceStub,
          bookingService: () =>
            sinon.createStubInstance<BookingService>(BookingService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          screenService: () =>
            sinon.createStubInstance<ScreenService>(ScreenService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(SeatUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.seat = seatSample;
        seatServiceStub.update.resolves(seatSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(seatServiceStub.update.calledWith(seatSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        seatServiceStub.create.resolves(entity);
        const wrapper = shallowMount(SeatUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.seat = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(seatServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        seatServiceStub.find.resolves(seatSample);
        seatServiceStub.retrieve.resolves([seatSample]);

        // WHEN
        route = {
          params: {
            seatId: '' + seatSample.id,
          },
        };
        const wrapper = shallowMount(SeatUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.seat).toMatchObject(seatSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        seatServiceStub.find.resolves(seatSample);
        const wrapper = shallowMount(SeatUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
