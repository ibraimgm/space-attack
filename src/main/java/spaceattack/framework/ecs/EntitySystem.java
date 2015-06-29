package spaceattack.framework.ecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

import spaceattack.framework.GameIO;

public final class EntitySystem
{
  long nextId = 0;
  HashMap<Class<? extends Component>, HashMap<Long, ? extends Component>> componentStore = new HashMap<Class<? extends Component>, HashMap<Long, ? extends Component>>();
  List<System> logicSystems = new ArrayList<System>();
  List<System> renderSystems = new ArrayList<System>();
  List<Consumer<EntitySystem>> pendingChanges = new ArrayList<Consumer<EntitySystem>>();
  
  public long newEntity()
  {
    return nextId++;
  }
  
  public <T extends Component> T getComponent(Long e, Class<T> componentClass)
  {
    HashMap<Long, ? extends Component> comps = componentStore.get(componentClass);
    
    if (comps != null)
    {
      @SuppressWarnings("unchecked")
      T comp = (T) comps.get(e);
      return comp;
    }
    else
      return null;
  }
  
  @SuppressWarnings("unchecked")
  public <T extends Component> Set<Entry<Long, T>> getAllComponents(Class<T> componentClass)
  {
    HashMap<Long, ? extends Component> comps = componentStore.get(componentClass);
    
    if (comps == null)
      return new HashSet<Map.Entry<Long,T>>();
    else
      return ((HashMap<Long, T>) comps).entrySet();
  }
   
  @SuppressWarnings("unchecked")
  public <T extends Component> void putComponent(Long e, T component)
  {    
    HashMap<Long, ? extends Component> comps = componentStore.get(component.getClass());
    
    if (comps == null)
    {
      comps = new HashMap<Long, T>();
      componentStore.put(component.getClass(), comps);
    }
   
    ((HashMap<Long, T>) comps).put(e, component);
  }
  
  public void removeEntity(Long e)
  {
    for (Class<? extends Component> c : componentStore.keySet())
    {
      HashMap<Long, ? extends Component> comps = componentStore.get(c);
      comps.remove(e);
    }
  }
  
  public <T extends Component> void removeComponent(Long e, Class<T> componentClass)
  {
    HashMap<Long, ? extends Component> comps = componentStore.get(componentClass);
    
    if (comps != null)
      comps.remove(e);
  }
  
  public void requestChange(Consumer<EntitySystem> request)
  {
    pendingChanges.add(request);
  }
  
  public void requestRemove(Long e)
  {
    requestChange(es -> es.removeEntity(e));
  }
  
  public void applyChanges()
  {
    for (Consumer<EntitySystem> change : pendingChanges)
      change.accept(this); 
    
    pendingChanges.clear();
  }
  
  public void addLogicSystem(System s)
  {
    logicSystems.add(s);
  }
  
  public void addRenderSystem(System s)
  {
    renderSystems.add(s);
  }
  
  public void runLogicSystems(GameIO io, double delta)
  {
    applyChanges();
    
    for (System s : logicSystems)
    {
      s.execute(this, io, delta);
      applyChanges();
    }
  }
  
  public void runRenderSystems(GameIO io, double delta)
  {
    applyChanges();
    
    for (System s : renderSystems)
    {
      s.execute(this, io, delta);
      applyChanges();
    }
  }
}
