/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import BookingUpdate from './booking-update.vue';
import BookingService from './booking.service';
import AlertService from '@/shared/alert/alert.service';

import MovieService from '@/entities/movie/movie.service';
import CustomerService from '@/entities/customer/customer.service';

type BookingUpdateComponentType = InstanceType<typeof BookingUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const bookingSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<BookingUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Booking Management Update Component', () => {
    let comp: BookingUpdateComponentType;
    let bookingServiceStub: SinonStubbedInstance<BookingService>;

    beforeEach(() => {
      route = {};
      bookingServiceStub = sinon.createStubInstance<BookingService>(BookingService);
      bookingServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          bookingService: () => bookingServiceStub,
          movieService: () =>
            sinon.createStubInstance<MovieService>(MovieService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          customerService: () =>
            sinon.createStubInstance<CustomerService>(CustomerService, {
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
        const wrapper = shallowMount(BookingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.booking = bookingSample;
        bookingServiceStub.update.resolves(bookingSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(bookingServiceStub.update.calledWith(bookingSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        bookingServiceStub.create.resolves(entity);
        const wrapper = shallowMount(BookingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.booking = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(bookingServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        bookingServiceStub.find.resolves(bookingSample);
        bookingServiceStub.retrieve.resolves([bookingSample]);

        // WHEN
        route = {
          params: {
            bookingId: '' + bookingSample.id,
          },
        };
        const wrapper = shallowMount(BookingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.booking).toMatchObject(bookingSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        bookingServiceStub.find.resolves(bookingSample);
        const wrapper = shallowMount(BookingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
