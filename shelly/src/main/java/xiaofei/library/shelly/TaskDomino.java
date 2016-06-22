/**
 *
 * Copyright 2016 Xiaofei
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package xiaofei.library.shelly;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import xiaofei.library.shelly.function.Action0;
import xiaofei.library.shelly.function.Action1;
import xiaofei.library.shelly.function.Function1;
import xiaofei.library.shelly.function.TargetAction0;
import xiaofei.library.shelly.function.TargetAction1;
import xiaofei.library.shelly.function.Player;
import xiaofei.library.shelly.util.Triple;
import xiaofei.library.shelly.scheduler.BackgroundQueueScheduler;
import xiaofei.library.shelly.scheduler.BackgroundScheduler;
import xiaofei.library.shelly.scheduler.DefaultScheduler;
import xiaofei.library.shelly.scheduler.NewThreadScheduler;
import xiaofei.library.shelly.scheduler.Scheduler;
import xiaofei.library.shelly.scheduler.UiThreadScheduler;

/**
 * Created by Xiaofei on 16/6/21.
 */
public class TaskDomino<T, R, U> extends Domino<T, Triple<Boolean, R, U>> {

    TaskDomino(Domino<T, Triple<Boolean, R, U>> domino) {
        super(domino.getLabel(), domino.getPlayer());
    }

    public TaskDomino<T, R, U> onSuccess(final Domino<R, ?> domino) {
        return new TaskDomino<T, R, U>(
                new Domino<T, Triple<Boolean, R, U>>(getLabel(),
                        new Player<T, Triple<Boolean, R, U>>() {
                            @Override
                            public Scheduler<Triple<Boolean, R, U>> call(List<T> input) {
                                final Scheduler<Triple<Boolean, R, U>> scheduler = getPlayer().call(input);
                                scheduler.play(new Player<Triple<Boolean, R, U>, Triple<Boolean, R, U>>() {
                                    @Override
                                    public Scheduler<Triple<Boolean, R, U>> call(List<Triple<Boolean, R, U>> input) {
                                        CopyOnWriteArrayList<R> newInput = new CopyOnWriteArrayList<R>();
                                        for (Triple<Boolean, R, U> triple : input) {
                                            if (triple.first) {
                                                newInput.add(triple.second);
                                            }
                                        }
                                        if (!newInput.isEmpty()) {
                                            domino.getPlayer().call(newInput);
                                        }
                                        return scheduler;
                                    }
                                });
                                return scheduler;
                            }
                        }
                ));
    }

    public <S> TaskDomino<T, R, U> onSuccess(final Class<S> clazz, final TargetAction0<S> targetAction0) {
        return new TaskDomino<T, R, U>(
                new Domino<T, Triple<Boolean, R, U>>(getLabel(),
                        new Player<T, Triple<Boolean, R, U>>() {
                            @Override
                            public Scheduler<Triple<Boolean, R, U>> call(List<T> input) {
                                final Scheduler<Triple<Boolean,R, U>> scheduler = getPlayer().call(input);
                                scheduler.play(new Player<Triple<Boolean, R, U>, Triple<Boolean, R, U>>() {
                                    @Override
                                    public Scheduler<Triple<Boolean, R, U>> call(List<Triple<Boolean, R, U>> input) {
                                        boolean success = false;
                                        for (Triple<Boolean, R, U> triple : input) {
                                            if (triple.first) {
                                                success = true;
                                                break;
                                            }
                                        }
                                        if (success) {
                                            CopyOnWriteArrayList<Object> objects = TARGET_CENTER.getObjects(clazz);
                                            for (Object object : objects) {
                                                targetAction0.call(clazz.cast(object));
                                            }
                                        }
                                        return scheduler;
                                    }
                                });
                                return scheduler;
                            }
                        }
                ));
    }

    public <S> TaskDomino<T, R, U> onSuccess(final Class<S> clazz, final TargetAction1<S, R> targetAction1) {
        return new TaskDomino<T, R, U>(
                new Domino<T, Triple<Boolean, R, U>>(getLabel(),
                        new Player<T, Triple<Boolean, R, U>>() {
                            @Override
                            public Scheduler<Triple<Boolean, R, U>> call(List<T> input) {
                                final Scheduler<Triple<Boolean, R, U>> scheduler = getPlayer().call(input);
                                scheduler.play(new Player<Triple<Boolean, R, U>, Triple<Boolean, R, U>>() {
                                    @Override
                                    public Scheduler<Triple<Boolean, R, U>> call(List<Triple<Boolean, R, U>> input) {
                                        CopyOnWriteArrayList<Object> objects = TARGET_CENTER.getObjects(clazz);
                                        for (Object object : objects) {
                                            for (Triple<Boolean, R, U> singleInput : input) {
                                                if (singleInput.first) {
                                                    targetAction1.call(clazz.cast(object), singleInput.second);
                                                }
                                            }
                                        }
                                        return scheduler;
                                    }
                                });
                                return scheduler;
                            }
                        }
                ));
    }

    public TaskDomino<T, R, U> onSuccess(final Action0 action0) {
        return new TaskDomino<T, R, U>(
                new Domino<T, Triple<Boolean, R, U>>(getLabel(),
                        new Player<T, Triple<Boolean, R, U>>() {
                            @Override
                            public Scheduler<Triple<Boolean, R, U>> call(List<T> input) {
                                final Scheduler<Triple<Boolean, R, U>> scheduler = getPlayer().call(input);
                                scheduler.play(new Player<Triple<Boolean, R, U>, Triple<Boolean, R, U>>() {
                                    @Override
                                    public Scheduler<Triple<Boolean, R, U>> call(List<Triple<Boolean, R, U>> input) {
                                        boolean success = false;
                                        for (Triple<Boolean, R, U> triple : input) {
                                            if (triple.first) {
                                                success = true;
                                                break;
                                            }
                                        }
                                        if (success) {
                                            action0.call();
                                        }
                                        return scheduler;
                                    }
                                });
                                return scheduler;
                            }
                        }
                ));
    }

    public TaskDomino<T, R, U> onSuccess(final Action1<R> action1) {
        return new TaskDomino<T, R, U>(
                new Domino<T, Triple<Boolean, R, U>>(getLabel(),
                        new Player<T, Triple<Boolean, R, U>>() {
                            @Override
                            public Scheduler<Triple<Boolean, R, U>> call(List<T> input) {
                                final Scheduler<Triple<Boolean, R, U>> scheduler = getPlayer().call(input);
                                scheduler.play(new Player<Triple<Boolean, R, U>, Triple<Boolean, R, U>>() {
                                    @Override
                                    public Scheduler<Triple<Boolean, R, U>> call(List<Triple<Boolean, R, U>> input) {
                                        for (Triple<Boolean, R, U> singleInput : input) {
                                            if (singleInput.first) {
                                                action1.call(singleInput.second);
                                            }
                                        }
                                        return scheduler;
                                    }
                                });
                                return scheduler;
                            }
                        }
                ));
    }

    //TODO 其实是player的高阶函数
    public TaskDomino<T, R, U> onFailure(final Domino<U, ?> domino) {
        return new TaskDomino<T, R, U>(
                new Domino<T, Triple<Boolean, R, U>>(getLabel(),
                        new Player<T, Triple<Boolean, R, U>>() {
                            @Override
                            public Scheduler<Triple<Boolean, R, U>> call(List<T> input) {
                                final Scheduler<Triple<Boolean, R, U>> scheduler = getPlayer().call(input);
                                scheduler.play(new Player<Triple<Boolean, R, U>, Triple<Boolean, R, U>>() {
                                    @Override
                                    public Scheduler<Triple<Boolean, R, U>> call(List<Triple<Boolean, R, U>> input) {
                                        //TODO 此处不必Copy
                                        CopyOnWriteArrayList<U> newInput = new CopyOnWriteArrayList<U>();
                                        for (Triple<Boolean, R, U> triple : input) {
                                            if (!triple.first) {
                                                newInput.add(triple.third);
                                            }
                                        }
                                        if (!newInput.isEmpty()) {
                                            domino.getPlayer().call(newInput);
                                        }
                                        return scheduler;
                                    }
                                });
                                return scheduler;
                            }
                        }
                ));
    }

    public <S> TaskDomino<T, R, U> onFailure(final Class<S> clazz, final TargetAction0<S> targetAction0) {
        return new TaskDomino<T, R, U>(
                new Domino<T, Triple<Boolean, R, U>>(getLabel(),
                        new Player<T, Triple<Boolean, R, U>>() {
                            @Override
                            public Scheduler<Triple<Boolean, R, U>> call(List<T> input) {
                                final Scheduler<Triple<Boolean, R, U>> scheduler = getPlayer().call(input);
                                scheduler.play(new Player<Triple<Boolean, R, U>, Triple<Boolean, R, U>>() {
                                    @Override
                                    public Scheduler<Triple<Boolean, R, U>> call(List<Triple<Boolean, R, U>> input) {
                                        boolean failure = false;
                                        for (Triple<Boolean, R, U> triple : input) {
                                            if (!triple.first) {
                                                failure = true;
                                                break;
                                            }
                                        }
                                        if (failure) {
                                            CopyOnWriteArrayList<Object> objects = TARGET_CENTER.getObjects(clazz);
                                            for (Object object : objects) {
                                                targetAction0.call(clazz.cast(object));
                                            }
                                        }
                                        return scheduler;
                                    }
                                });
                                return scheduler;
                            }
                        }
                ));
    }

    public <S> TaskDomino<T, R, U> onFailure(final Class<S> clazz, final TargetAction1<S, U> targetAction1) {
        return new TaskDomino<T, R, U>(
                new Domino<T, Triple<Boolean, R, U>>(getLabel(),
                        new Player<T, Triple<Boolean, R, U>>() {
                            @Override
                            public Scheduler<Triple<Boolean, R, U>> call(List<T> input) {
                                final Scheduler<Triple<Boolean, R, U>> scheduler = getPlayer().call(input);
                                scheduler.play(new Player<Triple<Boolean, R, U>, Triple<Boolean, R, U>>() {
                                    @Override
                                    public Scheduler<Triple<Boolean, R, U>> call(List<Triple<Boolean, R, U>> input) {
                                        CopyOnWriteArrayList<Object> objects = TARGET_CENTER.getObjects(clazz);
                                        for (Object object : objects) {
                                            for (Triple<Boolean, R, U> singleInput : input) {
                                                if (!singleInput.first) {
                                                    targetAction1.call(clazz.cast(object), singleInput.third);
                                                }
                                            }
                                        }
                                        return scheduler;
                                    }
                                });
                                return scheduler;
                            }
                        }
                ));
    }

    public TaskDomino<T, R, U> onFailure(final Action0 action0) {
        return new TaskDomino<T, R, U>(
                new Domino<T, Triple<Boolean, R, U>>(getLabel(),
                        new Player<T, Triple<Boolean, R, U>>() {
                            @Override
                            public Scheduler<Triple<Boolean, R, U>> call(List<T> input) {
                                final Scheduler<Triple<Boolean, R, U>> scheduler = getPlayer().call(input);
                                scheduler.play(new Player<Triple<Boolean, R, U>, Triple<Boolean, R, U>>() {
                                    @Override
                                    public Scheduler<Triple<Boolean, R, U>> call(List<Triple<Boolean, R, U>> input) {
                                        boolean failure = false;
                                        for (Triple<Boolean, R, U> triple : input) {
                                            if (!triple.first) {
                                                failure = true;
                                                break;
                                            }
                                        }
                                        if (failure) {
                                            action0.call();
                                        }
                                        return scheduler;
                                    }
                                });
                                return scheduler;
                            }
                        }
                ));
    }

    public TaskDomino<T, R, U> onFailure(final Action1<U> action1) {
        return new TaskDomino<T, R, U>(
                new Domino<T, Triple<Boolean, R, U>>(getLabel(),
                        new Player<T, Triple<Boolean, R, U>>() {
                            @Override
                            public Scheduler<Triple<Boolean, R, U>> call(List<T> input) {
                                final Scheduler<Triple<Boolean, R, U>> scheduler = getPlayer().call(input);
                                scheduler.play(new Player<Triple<Boolean, R, U>, Triple<Boolean, R, U>>() {
                                    @Override
                                    public Scheduler<Triple<Boolean, R, U>> call(List<Triple<Boolean, R, U>> input) {
                                        for (Triple<Boolean, R, U> singleInput : input) {
                                            if (!singleInput.first) {
                                                action1.call(singleInput.third);
                                            }
                                        }
                                        return scheduler;
                                    }
                                });
                                return scheduler;
                            }
                        }
                ));
    }

    public TaskDomino<T, R, U> finallyDo(Action0 action0) {
        return new TaskDomino<T, R, U>(target(action0));
    }

    public <S> TaskDomino<T, R, U> finallyDo(Class<S> clazz, TargetAction0<S> targetAction0) {
        return new TaskDomino<T, R, U>(target(clazz, targetAction0));
    }

    private Domino<T, Triple<Boolean, R, U>> toDomino() {
        return new Domino<T, Triple<Boolean, R, U>>(getLabel(), getPlayer());
    }

    public <S> Domino<T, S> endTask() {
        return toDomino().clear();
    }

    public <S> Domino<T, S> endTask(Function1<List<Triple<Boolean, R, U>>, S> reducer) {
        return toDomino().reduce(reducer);
    }

    @Override
    public TaskDomino<T, R, U> background() {
        return new TaskDomino<T, R, U>(
                new Domino<T, Triple<Boolean, R, U>>(getLabel(), new Player<T, Triple<Boolean, R, U>>() {
                    @Override
                    public Scheduler<Triple<Boolean, R, U>> call(List<T> input) {
                        Scheduler<Triple<Boolean, R, U>> scheduler = getPlayer().call(input);
                        return new BackgroundScheduler<Triple<Boolean, R, U>>(scheduler);
                    }
        }));
    }

    /**
     * For unit test only.
     */
    @Override
    TaskDomino<T, R, U> newThread() {
        return new TaskDomino<T, R, U>(
                new Domino<T, Triple<Boolean, R, U>>(getLabel(), new Player<T, Triple<Boolean, R, U>>() {
                    @Override
                    public Scheduler<Triple<Boolean, R, U>> call(List<T> input) {
                        Scheduler<Triple<Boolean, R, U>> scheduler = getPlayer().call(input);
                        return new NewThreadScheduler<Triple<Boolean, R, U>>(scheduler);
                    }
                }));
    }

    /**
     * For unit test only.
     */
    @Override
    TaskDomino<T, R, U> defaultScheduler() {
        return new TaskDomino<T, R, U>(
                new Domino<T, Triple<Boolean, R, U>>(getLabel(), new Player<T, Triple<Boolean, R, U>>() {
                    @Override
                    public Scheduler<Triple<Boolean, R, U>> call(List<T> input) {
                        Scheduler<Triple<Boolean, R, U>> scheduler = getPlayer().call(input);
                        return new DefaultScheduler<Triple<Boolean, R, U>>(scheduler);
                    }
                }));
    }

    @Override
    public TaskDomino<T, R, U> uiThread() {
        return new TaskDomino<T, R, U>(
                new Domino<T, Triple<Boolean, R, U>>(getLabel(), new Player<T, Triple<Boolean, R, U>>() {
                    @Override
                    public Scheduler<Triple<Boolean, R, U>> call(List<T> input) {
                        Scheduler<Triple<Boolean, R, U>> scheduler = getPlayer().call(input);
                        return new UiThreadScheduler<Triple<Boolean, R, U>>(scheduler);
                    }
                }));
    }

    @Override
    public TaskDomino<T, R, U> backgroundQueue() {
        return new TaskDomino<T, R, U>(
                new Domino<T, Triple<Boolean, R, U>>(getLabel(), new Player<T, Triple<Boolean, R, U>>() {
                    @Override
                    public Scheduler<Triple<Boolean, R, U>> call(List<T> input) {
                        Scheduler<Triple<Boolean, R, U>> scheduler = getPlayer().call(input);
                        return new BackgroundQueueScheduler<Triple<Boolean, R, U>>(scheduler);
                    }
                }));
    }

}